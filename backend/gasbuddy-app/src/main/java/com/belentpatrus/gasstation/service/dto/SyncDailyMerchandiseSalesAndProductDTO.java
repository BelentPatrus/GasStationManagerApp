package com.belentpatrus.gasstation.service.dto;

import com.belentpatrus.gasstation.model.dailysales.MerchandiseItemSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDailyMerchandiseSalesAndProductDTO {
    List<MerchandiseItemSaleDTO> notSyncedMerchandiseItemSales = new ArrayList<>();


}
