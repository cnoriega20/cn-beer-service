package api.tn.msvc.cnbeerservice.services.inventory;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;


@ConfigurationProperties(prefix = "cn.brewery", ignoreUnknownFields = false)
@Component
public class BeerInventoryServiceImpl implements BeerInventoryService {

    public static final String INVENTORY_PATH = "api/v1/beer/{beerId}/inventory";
    @Override
    public Integer getOnHandInventory(UUID beerId) {
        return null;
    }
}
