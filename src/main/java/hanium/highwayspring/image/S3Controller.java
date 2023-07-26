package hanium.highwayspring.image;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class S3Controller {
    private final ImageServiceImpl service;

    public S3Controller(ImageServiceImpl service) {
        this.service = service;
    }

    @ResponseBody
    @PostMapping(value="/diary/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long saveDiary(HttpServletRequest request, @ModelAttribute Image diary, @RequestParam(value="image") MultipartFile image) throws IOException {
        Long ImageId = service.keepImage(image, diary);
        return ImageId;
    }
}
