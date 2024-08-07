package me.amiralles.translator;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(chatMemoryProviderSupplier = // disable chat memory
RegisterAiService.NoChatMemoryProviderSupplier.class)
public interface TranslatorAiService {

    @SystemMessage("You are a professional translator.")
    @UserMessage("""
                Translate the text delimited by '---' into {language}.
                Do not include the delimiter in the translation.
                ---
                {text}
            """)
    String translate(String text, String language);
}
