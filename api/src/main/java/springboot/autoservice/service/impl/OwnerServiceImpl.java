package springboot.autoservice.service.impl;

import springboot.autoservice.model.Owner;
import springboot.autoservice.repository.OwnerRepository;
import springboot.autoservice.service.OwnerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner getById(Long id) {
        return ownerRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't get owner by id " + id));
    }

    @Override
    public Owner create(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }
}
