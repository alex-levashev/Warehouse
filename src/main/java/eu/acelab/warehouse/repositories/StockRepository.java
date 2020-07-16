package eu.acelab.warehouse.repositories;

import eu.acelab.warehouse.models.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Integer> {

}
