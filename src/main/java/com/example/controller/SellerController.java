package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.dto.ItemDTO;
import com.example.entity.BuyProjection;
import com.example.entity.ItemEntity;
import com.example.entity.MemberEntity;
import com.example.mapper.ItemMapper;
import com.example.repository.BuyRepository;
import com.example.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "seller")
public class SellerController {
    @Autowired
    ResourceLoader resLoader;

    @Autowired
    BuyRepository buyRepository;
    // JPA + HIBERNATE

    @Autowired
    ItemService iService;

    // int PAGECNT = 10;
    @Value("${board.page.count}")
    int PAGECNT;

    @Autowired
    ItemMapper iMapper;
    // MyBatis

    @GetMapping(value = "/deleteupdatebatch")
    public String deleteUpdateBatchGet(
            Model model,
            @RequestParam(name = "btn") String btn,
            @RequestParam(name = "no") Long[] no) {
        if (btn.equals("일괄수정")) {
            System.out.println(btn);
            System.out.println(no[0]);
            List<ItemEntity> list = iService.selectItemEntityIn(no);
            System.out.println(list);
            model.addAttribute("list", list);
            return "seller/updatebatch";
        }
        if (btn.equals("일괄삭제")) {
            iService.deletItemBatch(no);
        }
        return "redirect:/seller/home";
    }

    @PostMapping(value = "upadteActionbatch")
    public String upadteAction(
            @RequestParam(name = "icode") Long[] icode,
            @RequestParam(name = "iname") String[] iname,
            @RequestParam(name = "icontent") String[] icontent,
            @RequestParam(name = "iprice") Long[] iprice,
            @RequestParam(name = "iquantity") Long[] iquantity) {
        List<ItemEntity> list = new ArrayList<>();
        for (int i = 0; i < iname.length; i++) {
            ItemEntity item = new ItemEntity();
            item.setIcode(icode[i]);
            item.setIname(iname[i]);
            item.setIcontent(icontent[i]);
            item.setIprice(iprice[i]);
            item.setIquantity(iquantity[i]);

            list.add(item);
        }
        iService.updateItemBatch(list);
        return "redirect:/seller/home";
    }

    @GetMapping(value = "/insertitembatch")
    public String insertgetString() {
        return "/seller/insertBatch";
    }

    @PostMapping(value = "/insertitembatch")
    public String insertPosT(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "iname") String[] iname,
            @RequestParam(name = "icontent") String[] icontent,
            @RequestParam(name = "iprice") Long[] iprice,
            @RequestParam(name = "iquantity") Long[] iquantity,
            @RequestParam(name = "timage") MultipartFile[] iimage) throws IOException {

        List<ItemEntity> list = new ArrayList<>();
        for (int i = 0; i < iname.length; i++) {

            ItemEntity item = new ItemEntity();
            item.setIname(iname[i]);
            item.setIcontent(icontent[i]);
            item.setIprice(iprice[i]);
            item.setIquantity(iquantity[i]);
            item.setIimage(iimage[i].getBytes());
            item.setIimagename(iimage[i].getOriginalFilename());
            item.setIimagetype(iimage[i].getContentType());
            item.setIimagesize(iimage[i].getSize());

            MemberEntity member = new MemberEntity();
            member.setUemail(user.getUsername());
            item.setMember(member);
            list.add(item);
        }
        iService.insertItemBatch(list);
        return "redirect:/seller/home";

    }

    @GetMapping(value = { "/", "/home" })
    public String getSellerHome(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "txt", defaultValue = "") String txt,
            @AuthenticationPrincipal User user) {
        if (user != null) {

            // 목록
            List<ItemDTO> list = iMapper.selectItemList(
                    user.getUsername(), txt,
                    (page * PAGECNT) - (PAGECNT - 1),
                    page * PAGECNT);
            model.addAttribute("list", list);

            // 페이지네이션 개수
            long cnt = iMapper.selectItemCount(
                    user.getUsername(), txt);
            model.addAttribute("pages", (cnt - 1) / PAGECNT + 1);

            // 주문내역
            List<Long> list1 = new ArrayList();
            for (ItemDTO tmp : list) {
                list1.add(tmp.getIcode());
            }
            List<BuyProjection> buylist = buyRepository.findByItem_icodeIn(list1);
            model.addAttribute("buylist", buylist);
            return "seller/home";
        }
        return "redirect:/member/login";
    }

    @GetMapping(value = "/insertItem")
    public String getinsert() {
        return "seller/insert";
    }

    @PostMapping(value = "/insertaction")
    public String postInsertaction(
            // @ModelAttribute ItemDTO item,
            @RequestParam(name = "iname") String iname,
            @RequestParam(name = "icontent") String icontent,
            @RequestParam(name = "iprice") long iprice,
            @RequestParam(name = "iquantity") long iquantity,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "iimage2") MultipartFile file) throws IOException {

        ItemDTO item = new ItemDTO();
        item.setIcontent(icontent);
        item.setIname(iname);
        item.setIprice(iprice);
        item.setIquantity(iquantity);
        if (file != null) {
            item.setIimage(file.getBytes());
            item.setIimagesize(file.getSize());
            item.setIimagetype(file.getContentType());
            item.setIimagename(file.getOriginalFilename());
        }
        item.setUemail(user.getUsername());
        System.out.println(item);
        int ret = iMapper.insertItemOne(item);
        System.out.println(ret);
        return "redirect:/";
    }

    @PostMapping(value = "/deleteitem")
    public String postdeleteitem(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "code") long code) {
        if (user != null) {
            int ret = iMapper.deleteItemOne(code, user.getUsername());
            if (ret == 1) {
                return "redirect:/seller/home";
            }
            return "redirect:/seller/home";
        }
        return "redirect:member/login";
    }

    @GetMapping(value = "/updateItem")
    public String getUpdate(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "code") long code) {
        if (user != null) {
            ItemDTO item = iMapper.selectOne(user.getUsername(), code);
            model.addAttribute("item", item);
            return "seller/update";
        }
        return "redirect:/member/login";

    }

    @PostMapping(value = "/upadteAction")
    public String postupadteAction(
            @RequestParam(name = "image2") MultipartFile file,
            Model model,
            @AuthenticationPrincipal User user,
            @ModelAttribute ItemDTO item) throws IOException {
        if (user != null) {
            if (!file.isEmpty()) {
                item.setIimage(file.getBytes());
                item.setIimagesize(file.getSize());
                item.setIimagetype(file.getContentType());
                item.setIimagename(file.getOriginalFilename());
            }

            item.setUemail(user.getUsername());
            System.out.println(item);
            int ret = iMapper.updateOne(user.getUsername(), item);

            if (ret == 1) {
                model.addAttribute("msg", "수정완료되었습니다.");
                model.addAttribute("url", "/seller/home");
                return "alert";
            }
            model.addAttribute("msg", "수정실패되었습니다.");
            model.addAttribute("url", "/seller/updateItem?code=" + item.getIcode());
            return "alert";
        }
        return "redirect:/member/login";
    }

    @GetMapping(value = "/itemselectone")
    public String getSelectItemOne(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "code") long code) {

        ItemDTO item = iMapper.selectOne(user.getUsername(), code);
        model.addAttribute("item", item);
        return "seller/selectone";
    }

    @PostMapping(value = "insertImages")
    public String postInsertImage() {
        return "redirect:/seller/itemselectone?code=";
    }

}
