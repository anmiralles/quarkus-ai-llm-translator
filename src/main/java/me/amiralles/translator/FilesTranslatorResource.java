package me.amiralles.translator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
@Path("files")
public class FilesTranslatorResource {

    @Inject
    TranslatorAiService translatorAiService;
    
    //@Location("files.html")
    //Template template;

    // @GET
    // public TemplateInstance get() {
    //     return template.instance();
    // }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(FileUploadInput input) throws IOException {

        Map<String, String> data = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (FileUpload file : input.file) {
            data.put(file.fileName(), translatorAiService.translate(Files.readString(file.uploadedFile()), "english"));
        }

        return Response.ok(objectMapper.writeValueAsString(data)).build();
    }

    public static class FileUploadInput {
        @FormParam("input")
        public List<FileUpload> file;
    }

}