package co.istad.productapisimpledemo.service;
import co.istad.productapisimpledemo.dto.ProductRequest;
import co.istad.productapisimpledemo.dto.ProductResponse;
import co.istad.productapisimpledemo.dto.UpdateProductRequest;
import co.istad.productapisimpledemo.entity.Product;
import co.istad.productapisimpledemo.repository.ProductRepository;
import co.istad.productapisimpledemo.repository.ProductRepositoryOld;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultLifecycleProcessor;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    // inject the repository here
    //private final ProductRepositoryOld productRepositoryOld;
   private final ProductRepository productRepository;


    private Product mapToEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());

        return product;
    }
    // mapToResponse -> convert Entity to Response
    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        // create entity product from the request
        var product = mapToEntity(request);
        // set static userID
        product.setUserId(1);
      // insert the data to the table only need to
        // repository.save(entity) = insert
        return mapToResponse(productRepository.save(product));

    }

    @Override
    public List<ProductResponse> findAllProducts() {
        // repository.findAll()
      return productRepository.findAll()
              .stream()
              .map(this::mapToResponse)
              .toList();
    }

    @Override
    public ProductResponse findProductById(Integer id) {
       var product =  productRepository.findById(id)
               .orElseThrow(()-> new NoSuchElementException("Product with ID = "+id+" not found"));

        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Integer id , UpdateProductRequest request) {
        // find existing product
        // repository.findById
        var existingProduct = productRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Product with ID = "+id+" not found"));

        if(request.name()!=null)
            existingProduct.setName(request.name());
        if(request.description()!=null)
            existingProduct.setDescription(request.description());
        if(request.price()!=null)
            existingProduct.setPrice(request.price());
        // update product
        productRepository.save(existingProduct);
        return mapToResponse(existingProduct);
    }


    @Override
    public boolean deleteProduct(Integer id) {
        // find if the product exist
        // if it's we delete it and return true
        // else return false

        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
