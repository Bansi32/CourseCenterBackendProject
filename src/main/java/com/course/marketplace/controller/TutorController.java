package com.course.marketplace.controller;

import com.course.marketplace.dto.CourseDto;
import com.course.marketplace.dto.TutorDto;
import com.course.marketplace.entity.UserDetails;
import com.course.marketplace.service.CourseService;
import com.course.marketplace.service.TutorService;
import com.course.marketplace.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutors")
@CrossOrigin("*")
public class TutorController {

    @Autowired
    private TutorService tutorService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<Page<TutorDto>> searchTutors(@RequestParam(name="keyword",defaultValue = "")String keyword, @RequestParam(name="page",defaultValue = "0")int page, @RequestParam(name="size",defaultValue = "2")int size)
    {
        ResponseEntity responseEntity;
        try
        {
            responseEntity=new ResponseEntity(tutorService.findTutorsByName(keyword, page, size), HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity<>("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TutorDto>> findAllTutors()
    {
        ResponseEntity responseEntity;
        try{
            responseEntity=new ResponseEntity(tutorService.fetchTutor(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/{tutorId}")
    public  ResponseEntity<String> deleteTutor(@PathVariable Long tutorId)
    {
        ResponseEntity responseEntity;
        try{
            tutorService.removeTutor(tutorId);
            responseEntity=new ResponseEntity("Tutor Deleted Successfully!",HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping()
    public ResponseEntity<TutorDto> saveTutor(@RequestBody TutorDto tutorDto)
    {
        ResponseEntity responseEntity;
        try{
            UserDetails userDetails=userDetailsService.getUserByEmail(tutorDto.getUserDetailsDto().getEmail());
            if(userDetails!=null)
            {
                throw new RuntimeException("Email Already Exists!");
            }
            tutorService.createTutor(tutorDto);
            responseEntity=new ResponseEntity("Tutor created Successfully!",HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("/{tutorId}")
    public ResponseEntity<TutorDto> updateTutor(@RequestBody TutorDto tutorDto,@PathVariable Long tutorId)
    {
        ResponseEntity responseEntity;
        try{
            tutorDto.setTutorId(tutorId);
            tutorService.updateTutor(tutorDto);
            responseEntity=new ResponseEntity("Tutor Updated Successfully!",HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/{tutorId}/courses")
    public ResponseEntity<Page<CourseDto>> coursesByTutorId(@PathVariable Long tutorId,@RequestParam(name="page",defaultValue = "0") int page,@RequestParam(name="size",defaultValue = "2") int size)
    {
        ResponseEntity responseEntity;
        try{
          responseEntity=new ResponseEntity(courseService.fetchCoursesForTutor(tutorId,page,size) ,HttpStatus.OK);
        }
        catch(Exception e)
        {
          responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
        //return courseService.fetchCoursesForTutor(tutorId,page,size);
    }

    @GetMapping("/find")
    public ResponseEntity<TutorDto> getTutorByEmail(@RequestParam(name="email",defaultValue = "")String email)
    {
        ResponseEntity responseEntity;
        try{
            responseEntity=new ResponseEntity(tutorService.getTutorByEmail(email) ,HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/non-activated")
    public ResponseEntity<TutorDto> getNonActivatedTutors()
    {
        ResponseEntity responseEntity;
        try {
            responseEntity=new ResponseEntity(tutorService.fetchInActivatedTutor() ,HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/activated")
    public ResponseEntity<TutorDto> getActivatedTutors()
    {
        ResponseEntity responseEntity;
        try {
            responseEntity=new ResponseEntity(tutorService.fetchActivatedTutor() ,HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
