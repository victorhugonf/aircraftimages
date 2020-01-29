package io.github.victorhugonf.aircraftimages.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.github.victorhugonf.aircraftimages.controller.dto.AircraftImageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/aircraftimages")
public class AircraftImagesController {

    @GetMapping("/{registration}")
    //public RedirectView redirectByRegistration(@PathVariable String registration) throws IOException {
    public ResponseEntity<AircraftImageDTO> redirectByRegistration(@PathVariable String registration) throws IOException {
        String url = String.format("https://www.jetphotos.com/showphotos.php?aircraft=all&airline=all&country-location=all&photographer-group=all&category=all&keywords-type=reg&keywords-contain=0&keywords=%s&photo-year=all&genre=all&search-type=Advanced&sort-order=0", registration);

        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(url);
        DomNodeList<DomElement> images = page.getElementsByTagName("img");

        List<String> photos = new ArrayList<>();

        for (DomElement image : images) {
            String src = image.getAttribute("src");
            if(src.contains("//cdn.jetphotos.com/400/6/")){
                photos.add(String.format("https:%s", src.replace("/400/", "/full/")));
                break;
            }
        }

        if(!photos.isEmpty()){
            AircraftImageDTO image = AircraftImageDTO.builder()
                    .registration(registration.toUpperCase())
                    .imageLink(photos.get(0)).build();

            return ResponseEntity.ok(image);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /*
    @GetMapping("/{registration}")
    //public RedirectView redirectByRegistration(@PathVariable String registration) throws IOException {
    public ResponseEntity<List<AircraftImageDTO>> redirectByRegistration(@PathVariable String registration) throws IOException {
        String url = String.format("https://www.jetphotos.com/showphotos.php?aircraft=all&airline=all&country-location=all&photographer-group=all&category=all&keywords-type=reg&keywords-contain=0&keywords=%s&photo-year=all&genre=all&search-type=Advanced&sort-order=0", registration);

        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(url);
        DomNodeList<DomElement> photos = page.getElementsByTagName("img");

        List<AircraftImageDTO> images = new ArrayList<>();

        for (DomElement photo : photos) {
            String src = photo.getAttribute("src");
            if(src.contains("//cdn.jetphotos.com/400/6/")){
                String link = String.format("https:%s", src.replace("/400/", "/full/"));

                AircraftImageDTO image = AircraftImageDTO.builder()
                        .registration(registration.toUpperCase())
                        .imageLink(link).build();

                images.add(image);
                //break;
            }
        }

        //return new RedirectView(photos.get(0));

        if(!photos.isEmpty()){
            return ResponseEntity.ok(images);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    */

}

