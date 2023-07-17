package com.example.ezhcm.service.ref_item;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.rep.RefRefItem;
import com.example.ezhcm.repostiory.RefRefItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefRefItemService implements IRefRefItemService {
    private final RefRefItemRepository itemRepository;

    public RefRefItemService(RefRefItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Optional<RefRefItem> findById(Long aLong) {
        Optional<RefRefItem> item = itemRepository.findById(aLong);
        if (item.isPresent()) {
            return item;
        } else throw new CustomException(ErrorCode.NOT_FOUND, "Không tìm thấy ref item" + aLong);

    }

    @Override
    public List<RefRefItem> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public RefRefItem save(RefRefItem refRefItem) {
        return itemRepository.save(refRefItem);
    }

    @Override
    public void delete(Long aLong) {
        itemRepository.deleteById(aLong);
        Log.info("Delete reference Item id " + aLong);
    }

    @Override
    public List<RefRefItem> findAllByReferenceId(Long referenceId) {
        return itemRepository.findAllByRefReferenceId(referenceId);
    }
}
