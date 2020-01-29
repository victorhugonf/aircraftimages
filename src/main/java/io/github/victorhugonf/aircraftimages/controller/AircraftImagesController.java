package io.github.victorhugonf.aircraftimages.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.github.victorhugonf.aircraftimages.controller.dto.AircraftImageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aircraftimages")
public class AircraftImagesController {
/*
    private JetPhotosFeign jetPhotosFeign;

    public AircraftImagesController(){
        jetPhotosFeign = Feign.builder()
                .decoder(new HtmlDecoder())
                .target(JetPhotosFeign.class, "https://www.jetphotos.com");
    }
*/
   /* @GetMapping("/{registration}")
    public ResponseEntity<String> byRegistration(@PathVariable String registration) throws IOException {
        //String jetPhotosResult = jetPhotosFeign.getByRegistration(registration);
        //System.out.println(jetPhotosResult);

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
            Page pagePhoto = webClient.getPage(photos.get(0));
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }


        //ACESSAR:
        //    https://www.jetphotos.com/showphotos.php?aircraft=all&airline=all&country-location=all&photographer-group=all&category=all&keywords-type=reg&keywords-contain=0&keywords=cc-bga&photo-year=all&genre=all&search-type=Advanced&sort-order=0

        //BUSCAR PELAS TAGS NA RESPOSTA:
        //    <img src="//cdn.jetphotos.com/400/6/75918_1492684790.jpg" class="result__photo" onerror="this.src='/assets/img/placeholders/medium.jpg'" alt="CC-BGA - Boeing 787-9 Dreamliner - LATAM Airlines " title="Photo of CC-BGA - Boeing 787-9 Dreamliner - LATAM Airlines ">

        //DOWNLOAD DA IMAGEM EM:
        //    https://cdn.jetphotos.com/full/6/75918_1492684790.jpg




        //return ResponseEntity.ok(page.getWebResponse().getContentAsString());
    }
*/

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

        //return new RedirectView(photos.get(0));

        if(!photos.isEmpty()){
            AircraftImageDTO image = AircraftImageDTO.builder()
                    .registration(registration.toUpperCase())
                    .imageLink(photos.get(0)).build();

            return ResponseEntity.ok(image);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}

