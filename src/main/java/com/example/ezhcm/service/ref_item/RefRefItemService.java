package com.example.ezhcm.service.ref_item;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.model.rep.RefRefItem;
import com.example.ezhcm.repostiory.RefRefItemRepository;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefRefItemService implements IRefRefItemService {
    private final RefRefItemRepository itemRepository;
    private final IAutoPkSupportService autoPkSupportService;


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
    @Override
    public RefRefItem createRefRefItem(RefRefItem refRefItem) {
        Long id = autoPkSupportService.generateId(Constants.REF_ITEM);
        refRefItem.setRefItemId(id);
        save(refRefItem);
        return refRefItem;
    }
}
