package com.polarbookshop.catalog_service.demo;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")//assegna alla classe il profilo testdata, sarà registrata solo quando il profilo testdata è attivo
public class BookDataLoader {

    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /**
     * La generazione dei dati di test è triggerata quando un ApplicationReadyEvent viene mandato -
     * sarebbe quando la fase di avvio dell'applicazione è completata
     */
    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var book1 = new Book("1234567891", "Northern Lights", "Lyra Silverstar", 9.90);
        var book2 = new Book("1234567892", "Polar Journey", "Iorek Polarson", 12.90);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}