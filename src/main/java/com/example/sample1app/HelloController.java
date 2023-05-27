package com.example.sample1app;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sample1app.repositories.PersonRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Controller
public class HelloController {
    @Autowired
    PersonRepository repository;

    @PostConstruct
    public void init() {
        // 1つ目のダミーデータ
        Person p1 = new Person();
        p1.setName("taro");
        p1.setMail("taro@yamada");
        p1.setAge(25);
        repository.saveAndFlush(p1);
        // 2つ目のダミーデータ
        Person p2 = new Person();
        p2.setName("hanako");
        p2.setMail("hanako@flower");
        p2.setAge(30);
        repository.saveAndFlush(p2);
        // 3つ目のダミーデータ
        Person p3 = new Person();
        p3.setName("sachiko");
        p3.setMail("sachiko@happy");
        p3.setAge(35);
        repository.saveAndFlush(p3);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(
            @ModelAttribute("formModel") Person Person,
            ModelAndView mav) {
        mav.setViewName("index");
        mav.addObject("title", "Hello Page");
        mav.addObject("msg", "this is JPA sample data.");
        List<Person> list = repository.findAll();
        mav.addObject("data", list);
        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Transactional
    public ModelAndView form(
            @ModelAttribute("formModel") Person Person,
            ModelAndView mav) {
        repository.saveAndFlush(Person);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(
            @ModelAttribute("formModel") Person Person,
            @PathVariable int id,
            ModelAndView mav) {
        mav.setViewName("edit");
        mav.addObject("title", "edit Person data.");
        Optional<Person> data = repository.findById((long) id);

        mav.addObject("formModel", data.get());
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Transactional
    public ModelAndView update(
            @ModelAttribute("formModel") Person Person,
            ModelAndView mav) {
        repository.saveAndFlush(Person);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(
            @PathVariable int id,
            ModelAndView mav) {
        mav.setViewName("delete");
        mav.addObject("title", "delete Person data.");
        mav.addObject("msg", "Can I delete this data?");
        Optional<Person> data = repository.findById((long) id);
        mav.addObject("formModel", data.get());
        return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional
    public ModelAndView remove(
            @RequestParam long id,
            ModelAndView mav) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/");
    }
}
