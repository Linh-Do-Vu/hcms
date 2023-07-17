package com.example.ezhcm.service.ip_authenic;

import com.example.ezhcm.model.IpAuthentic;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.repostiory.IpAuthenticRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class IpAuthenticService implements IIpAuthenticService{

    private final IpAuthenticRepository ipAuthenticRepository;

    public IpAuthenticService(IpAuthenticRepository ipAuthenticRepository) {
        this.ipAuthenticRepository = ipAuthenticRepository;
    }


    @Override
    public Optional<IpAuthentic> findById(Long aLong) {
        Log.info("IpAuthenticService.findByID");
        return ipAuthenticRepository.findById(aLong);
    }

    @Override
    public List<IpAuthentic> findAll() {
        Log.info("IpAuthenticService.findAll");

        return ipAuthenticRepository.findAll();
    }

    @Override
    public IpAuthentic save(IpAuthentic ipAuthentic) {
        Log.info("IpAuthenticService.findAll");
        return ipAuthenticRepository.save(ipAuthentic);
    }

    @Override
    public void delete(Long aLong) {
        Log.info("IpAuthenticService.delete");
        ipAuthenticRepository.deleteById(aLong);

    }
}
