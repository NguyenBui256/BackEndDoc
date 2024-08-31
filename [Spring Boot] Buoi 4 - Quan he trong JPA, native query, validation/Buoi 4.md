- Các mối quan hệ(1..N, N..N) trong JPA (@ManyToMany, @OneToMany,...)
- Jpa native query
- Validation (@Valid, @NotNull, @Nullable, @Length, ...)
- Xử lý ngoại lệ (@ControllerAdvice)
- Tối ưu query khi sử dụng JPA (n+1 query,...)

# Buổi 4 - Quan hệ trong JPA, native query, exception handling

# 1. Các mối quan hệ trong JPA

- Có thể ánh xạ mỗi quan hệ này dưới dạng **unidirectional (một chiều)** hoặc **bidirectional (hai chiều)**. Nghĩa là bạn có thể mô hình hoá chúng dưới dạng thuộc tính của một entity hoặc cả hai entity. Điều này không ảnh hưởng tới ánh xạ cơ sở dữ liệu, nhưng nó xác định chiều mà bạn có thể sử dụng mối quan hệ trong model và JPQL hoặc Criteria queries.

### 1.1 @OneToOne, @OneToMany, @ManyToOne

Mặc định Hibernate sẽ tự sinh tên của cột khoá ngoại dựa trên tên của thuộc tính ánh xạ quan hệ và tên của thuộc tính khoá chính. Ở ví dụ này, Hibernate sẽ sử dụng một cột với tên `order_id` để lưu khoá ngoại tới **Order** entity.

```Java
@Entity
public class OrderItem {
 
    @ManyToOne
    private Order order;
 
    …
}
```
Nếu muốn sử dụng một cột khác, cần phải định nghĩa cột khoá ngoại với `@JoinColumn` annotation. Ví dụ đoạn code dưới đây yêu cầu Hibernate sử dụng cột **fk_order** để lưu khoá ngoại.
```Java
@Entity
public class OrderItem {
 
    @ManyToOne
    @JoinColumn(name = “fk_order”)
    private Order order;
 
    …
}
```

- **Bidirectional:**
```Java
@Entity
public class Order {
    /* 
    mappedBy tên của thuộc tính gắn @ManyToOne ở 
    trong class OrderItem
    */
    @OneToMany(mappedBy = “order”) 
    private List<OrderItem> items = new ArrayList<OrderItem>();
 
    …
}
```

- Có thể triển khai một phương thức helper để cập nhật cả 2 entity (2 chiều)
```Java
@Entity
public class Order {
    …
         
    public void addItem(OrderItem item) {
        this.items.add(item);
        item.setOrder(this);
    }
    …
}
```

### 1.2 @ManyToMany
- Anh xạ quan hệ many-to-many, nên sử dụng `Set` thay vì `List` cho thuộc tính, để thuận tiện trong việc xóa các phần tử (lưu bằng List sẽ phải duyệt lại để xóa, Set thì xóa trực tiếp)
```Java
@Entity
public class Store {
 
    @ManyToMany
    private Set<Product> products = new HashSet<Product>();
 
    …
}
```
- Nếu không cung cấp thêm thông tin gì, Hibernate sẽ sử dụng ánh xạ mặc định với một bảng liên kết với tên của hai entity và các thuộc tính khoá chính của hai entity. Trong trường hợp này, Hibernate sử dụng bảng `Store_Product` với các cột `store_id` và `product_id`.
- Có thể tuỳ biến điều này với **`@JoinTable`** annotation cùng các thuộc tính của nó là `joinColumns` và `inverseJoinColumns`.
  - Thuộc tính `joinColumns` xác định các cột khoá ngoại cho entity mà bạn định nghĩa ánh xạ quan hệ. 
  - Thuộc tính `inverseJoinColumns` xác định các cột khoá ngoại của entity liên kết.
```Java
@Entity
public class Store {
 
    @ManyToMany
    @JoinTable(name = “store_product”,
           joinColumns = { @JoinColumn(name = “fk_store”) },
           inverseJoinColumns = { @JoinColumn(name = “fk_product”) })
    private Set<Product> products = new HashSet<Product>();
 
    …
}
```
- **Bidirectional:**
```Java
@Entity
public class Product{
 
    @ManyToMany(mappedBy=”products”)
    private Set<Store> stores = new HashSet<Store>();
 
    …
}
```
- **Hàm cập nhật 2 chiều:**
```Java
@Entity
public class Store {
 
    public void addProduct(Product p) {
        this.products.add(p);
        p.getStores().add(this);
    }
 
    public void removeProduct(Product p) {
        this.products.remove(p);
        p.getStores().remove(this);
    }
 
    …
}
```

# 2. Native query
- Khi sử dụng JPA thuần hoặc Hibernate, việc định nghĩa và thực hiện một native query yêu cầu nhiều bước.
- Khi định nghĩa một native query, chúng ta chú thích repository method của mình bằng anotation **`@Query`**, đặt thuộc tính `nativeQuery=true` và cung cấp một câu lệnh SQL làm giá trị. Trong ví dụ dưới đây, chúng ta có thể sử dụng các tham số giống như trong custom JPQL query.
```Java
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {
 
    @Query(value="select * from author a where a.first_name= :firstName", nativeQuery=true)
    List<Author> getAuthorsByFirstName(String firstName);
   
}
```
- Khi dùng native query với các thao tác ghi, cần thêm annotation **@Modifying**, khác với thao tác đọc không cần.
```Java
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {
 
    @Modifying
    @Query(value="delete from author a where a.last_name= :lastName", nativeQuery = true)
    void deleteAuthorByLastName(@Param("lastName") String lastName);
     
    @Modifying
    @Query(value="update author set last_name= :lastName where first_name = :firstName", nativeQuery=true)
    void updateAuthorByFirstName(String firstName, String lastName);
}
```
- **Hạn chế:**
  - Cần đảm bảo DBMS sử dụng có hỗ trợ câu lệnh SQL, vì native query viết theo SQL, khác với custom JPQL query là không phụ thuộc vào SQL
  - Không hỗ trợ sắp xếp tự động, phải thêm `ORDER BY` trong query.
  - Để sử dụng phân trang cần bổ sung thêm **count query**

# 3. Validation