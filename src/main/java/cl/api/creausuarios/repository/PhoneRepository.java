package cl.api.creausuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.api.creausuarios.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long>{

}

