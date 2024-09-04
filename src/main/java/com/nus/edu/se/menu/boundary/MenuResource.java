package com.nus.edu.se.menu.boundary;

import com.nus.edu.se.menu.dao.MenuRepository;
import com.nus.edu.se.menu.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("menuAPI")
public class MenuResource {

    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    @GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getMenus() {
        return menuRepository.findAll();
    }
}
