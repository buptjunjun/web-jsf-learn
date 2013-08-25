package org.junju.controller.part1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import javax.validation.Valid;

import org.junjun.Exception.part1.ImageUploadException;
import org.junjun.bean.part1.Form1;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Scope(value="prototype")
@RequestMapping("/form")
public class FormController {
	 
	
	@RequestMapping( method = RequestMethod.GET)
	 public String editForm (Model model ){		
				Form1 form = new Form1();
			 form.setName("todo33");
			 List<String> strs = new ArrayList<String>();
			 strs.add("aa");
			 strs.add("bb");
			 form.setListStr(strs);
			  model.addAttribute("form", form);
	         return "form";
    }
	
	
	@RequestMapping(method = RequestMethod.POST)
	 public String dealwithForm (@ModelAttribute("form") @Valid Form1 formaa ,BindingResult bindingResult,
			 				@RequestParam(value="image",required=false) MultipartFile image)
	{
		for(String str:formaa.getListStr())
		{
			System.out.println(str);
		}
		if(!image.isEmpty())
		try
		{
			if(validateImage(image))
			{
				this.saveImage("test.jpg", image);
			}
		}
		catch(ImageUploadException e)
		{
			bindingResult.rejectValue(null, "image", e.getMessage());
		}
		
	   if(bindingResult.hasErrors())
		   return "form";
	    return "redirect:/form/"+formaa.getName()+"/"+formaa.getAge();
	}
	 @RequestMapping(value="/{name}/{age}", method=RequestMethod.GET)
	 public String showformcontent(@PathVariable("name") String formname,@PathVariable String age,Model model)
	 {
		 model.addAttribute("name", formname);
		 model.addAttribute("age", age);
		 return "formcontent";
	 } 
	 
	 public boolean validateImage(MultipartFile image) throws  ImageUploadException
	 {
		 if(!image.getContentType().equals("image/jpeg")) {
				 throw new ImageUploadException("Only JPG images accepted");
				 }
		 return true;
	 }
	 
	 private void saveImage(String filename, MultipartFile image)
			 throws ImageUploadException {
			 try {
				 
			 File file = new File("/home/junjun/" + "/resources/" + filename);
			 FileUtils.writeByteArrayToFile(file, image.getBytes());
			 } catch (IOException e) {
			 throw new ImageUploadException("Unable to save image");
			 }
			 }
}

