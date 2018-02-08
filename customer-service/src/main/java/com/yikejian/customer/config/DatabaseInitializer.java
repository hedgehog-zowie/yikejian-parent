package com.yikejian.customer.config;

import com.yikejian.customer.domain.title.Title;
import com.yikejian.customer.repository.TitleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * <code>DatabaseInitializer</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/26 10:56
 */
@Service
@Profile({"development"})
public class DatabaseInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

    private TitleRepository titleRepository;

    @Autowired
    public DatabaseInitializer(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public void initForDevelop() {
        Title title = new Title();
        title.setTitleName("加班狗");
        title.setDeleted(0);
        title.setEffective(1);
        titleRepository.save(title);
    }

}
