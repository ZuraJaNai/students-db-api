package no.itera.dao;

import no.itera.model.Attachment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AttachmentDao extends CrudRepository<Attachment,Integer> {

}
