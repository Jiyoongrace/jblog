package com.poscodx.jblog.controller;

import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
    public String index(
            @PathVariable("id") String id,
            @PathVariable("categoryNo") Optional<Long> categoryNo,
            @PathVariable("postNo") Optional<Long> postNo,
            Model model) {

        BlogVo blogVo = blogService.getBlogById(id);
        if(blogVo == null) {
            throw new IllegalStateException();
        }

        model.addAttribute("blogVo", blogVo);

        List<Map<String, Object>> list = blogService.getCategoryById(id);
        model.addAttribute("categoryList", list);

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        categoryNo.ifPresent(category -> map.put("categoryNo", category));
        postNo.ifPresent(post -> map.put("postNo", post));

//        List<PostVo> postAllList = blogService.getPostList(id);
//        System.out.println(postAllList);

        if(categoryNo.isPresent() && postNo.isEmpty()) {
            // 카테고리 분류
            List<PostVo> postTopCategory = blogService.getTopCategoryPost(map);
            model.addAttribute("postNow", postTopCategory);
            System.out.println("postTopCategory: " + postTopCategory);

            List<PostVo> postList = blogService.getPostById(map);
            model.addAttribute("postList", postList);
            System.out.println(postList);
        } else if(postNo.isPresent()) {
            // post 띄우기
            List<PostVo> postNow = blogService.getCurrentPost(map);
            model.addAttribute("postNow", postNow);
            System.out.println("postNow: " + postNow);

            List<PostVo> postList = blogService.getPostList(id);
            model.addAttribute("postList", postList);
            System.out.println(postNow);
        } else {
            // 전체 post 띄우기`
            List<PostVo> postOne = blogService.getOnePost(map);
            model.addAttribute("postNow", postOne);
            System.out.println("postOne: " + postOne);
//            if(postOne.isEmpty()) {
//                postOne = null;
//            }

            List<PostVo> postList = blogService.getPostList(id);
            model.addAttribute("postList", postList);
            System.out.println(postList);
        }

        return "blog/main";
    }



    @Auth
    @RequestMapping("/admin/basic")
    public String adminBasic(HttpServletRequest req, @AuthUser UserVo authUser, @PathVariable("id") String id, Model model) {

        BlogVo vo = blogService.getBlogById(authUser.getId());
        if (vo == null || !(authUser.getId().equals(id))) {
            throw new IllegalArgumentException();
        }

        BlogVo blogVo = blogService.getBlogById(id);
        model.addAttribute("blogVo", blogVo);

        return "blog/admin-basic";
    }

    @Auth
    @PostMapping("/admin/basic/modify")
    public String adminBasic(@AuthUser UserVo authUser,
                             @PathVariable("id") String id,
                             @RequestParam("title") String title,
                             @RequestParam("file") MultipartFile file,
                             Model model) {

        BlogVo vo = blogService.getBlogById(authUser.getId());
        if (vo == null || !(authUser.getId().equals(id))) {
            throw new IllegalArgumentException();
        }

        // file과 title 둘 다 없으면 에러 처리
        if (file.isEmpty() && (title == null || title.isEmpty())) {
            throw new IllegalArgumentException();
        }

        // 파일이 존재하면 파일을 업로드하고 프로필 URL을 반환
        String profile = null;
        if (!file.isEmpty()) {
            profile = fileUploadService.restore(file);
        }

        // BlogVo 업데이트
        BlogVo blogVo = new BlogVo();
        blogVo.setId(authUser.getId());
        blogVo.setTitle(title);

        if (profile != null) {
            blogVo.setLogo(profile);
            System.out.println(profile);
        } else {
            blogVo.setLogo(vo.getLogo());
        }

        blogService.modifyBlog(blogVo);
        model.addAttribute("blogVo", blogVo);
        System.out.println(blogVo);

        return "blog/admin-basic";
    }

    @Auth
    @RequestMapping("/admin/category")
    public String adminCategory(@AuthUser UserVo authUser,
                                @PathVariable("id") String id,
                                Model model) {

        BlogVo vo = blogService.getBlogById(authUser.getId());
        if (vo == null || !(authUser.getId().equals(id))) {
            throw new IllegalArgumentException();
        }

        BlogVo blogVo = blogService.getBlogById(id);
        model.addAttribute("blogVo", blogVo);

        List<Map<String, Object>> category = blogService.getCategoryById(authUser.getId());
        model.addAttribute("list", category);

        return "blog/admin-category";
    }

    @Auth
    @PostMapping("/admin/category/add")
    public String adminCategoryAdd(@AuthUser UserVo authUser,
                                   @PathVariable("id") String id,
                                   String name,
                                   String description,
                                   Model model) {

        BlogVo vo = blogService.getBlogById(authUser.getId());
        if (vo == null || !(authUser.getId().equals(id))) {
            throw new IllegalArgumentException();
        }

        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setBlogId(authUser.getId());
        categoryVo.setName(name);
        categoryVo.setDescription(description);

        blogService.addCategory(categoryVo);

        return "redirect:/" + authUser.getId() + "/admin/category";
    }

    @Auth
    @RequestMapping("/admin/category/delete/{no}")
    public String adminCategoryDelete(@AuthUser UserVo authUser,
                                      @PathVariable("id") String id,
                                      @PathVariable("no") Long no,
                                      Model model) {
        BlogVo vo = blogService.getBlogById(authUser.getId());
        if (vo == null || !(authUser.getId().equals(id))) {
            throw new IllegalArgumentException();
        }

        // 포스트가 존재할 경우, 미분류로 이동, 그 외에는 삭제
        int postCount = blogService.postCountByCategoryNo(no);
        if(postCount != 0) {
            blogService.changeNoCatagery(no, authUser.getId());
            blogService.deleteCategory(no);
        } else {
            blogService.deleteCategory(no);
        }
        return "redirect:/" + authUser.getId() + "/admin/category";
    }

    @Auth
    @RequestMapping("/admin/write")
    public String adminWrite(@AuthUser UserVo authUser,
                             @PathVariable("id") String id,
                             Model model) {

        BlogVo vo = blogService.getBlogById(authUser.getId());
        if (vo == null || !(authUser.getId().equals(id))) {
            throw new IllegalArgumentException();
        }

        BlogVo blogVo = blogService.getBlogById(id);
        model.addAttribute("blogVo", blogVo);

        List<Map<String, Object>> category = blogService.getCategoryById(authUser.getId());
        model.addAttribute("list", category);

        return "blog/admin-write";
    }

    @Auth
    @PostMapping("/admin/write")
    public String adminWrite(@AuthUser UserVo authUser,
                             @PathVariable("id") String id,
                             String title,
                             String contents,
                             Long category_no,
                             Model model) {

        BlogVo vo = blogService.getBlogById(authUser.getId());
        if (vo == null || !(authUser.getId().equals(id))) {
            throw new IllegalArgumentException();
        }

        PostVo postVo = new PostVo();
        postVo.setTitle(title);
        postVo.setContents(contents);
        postVo.setCategoryNo(category_no);

        blogService.addPost(postVo);
        System.out.println(postVo);

        return "redirect:/" + authUser.getId();
    }
}
