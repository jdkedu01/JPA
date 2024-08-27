package org.example;

import jakarta.persistence.*;
import org.example.domain.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DB_Conn_Query_JPA {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("oracle-example-unit");
        // persistence.xml 파일 필요
        // Spring Framework에서 EntityManager가 자동 DI되면 위 구문은 불필요. xml 파일도 불필요
    EntityManager em = emf.createEntityManager();
    //@Transactional  - 자동 DI & 자동 트랜잭션 처리하고자할 때
    public void sqlRun() {
        // INSERT 문 실행 (JPA 사용)
        em.getTransaction().begin();
        Customer newCustomer = new Customer();
        newCustomer.setCustomerId("Kk-wmelon");
        newCustomer.setName("박수박");
        newCustomer.setAge(30);
        newCustomer.setMembershipLevel("gold");
        newCustomer.setOccupation("학생");
        newCustomer.setPoints(4500);
            // 과정이 각 속성별로 입력해야 해서 길어짐
            // rombok & builder를 이용하면 간단히 표현됨
        em.persist(newCustomer);
        em.flush(); // optional (빠른 전파를 바란다면 사용
        em.getTransaction().commit();

        // SELECT 쿼리 실행 (JPA 사용)
        List<Customer> customers = em.createQuery("select m from Customer m", Customer.class)
                .getResultList();  // JPQL로 작성. 훨씬간단. select m --- 객체를 검색. 일반적인 CRUD에 적합

        System.out.println("\t 고객ID \t 고객이름 \t 적립금 ");
        System.out.println("================================ ");
        for (Customer customer : customers) {
            System.out.println("\t" + customer.getCustomerId() + "\t" + customer.getName() + "\t" + customer.getPoints());
        }

        // CallableStatement를 사용한 저장 프로시저 호출 및 CURSOR 처리 (JPA 사용)
        Query query = em.createStoredProcedureQuery("SP_잠재고객")
                .registerStoredProcedureParameter(1, void.class, jakarta.persistence.ParameterMode.REF_CURSOR);
            // void.class를 사용한 것은 이 커서가 특정 엔티티 클래스나 데이터 타입으로 직접 매핑되지 않음을 나타내며,
            // 결과 처리에 대한 구체적인 타입 지정을 피하고자 할 때(반환유형에 무관하게) 사용됩니다.
        List<Object[]> potentialCustomers = query.getResultList();

        System.out.println("====== 잠재고객 명단입니다.======");
        for (Object[] row : potentialCustomers) {
            System.out.println(row[0] + ",\t" + row[1]);
        }
        em.close();
        //emf.close();
    }

    public static void main(String[] args) {
        DB_Conn_Query_JPA dbConQuery = new DB_Conn_Query_JPA();
        dbConQuery.sqlRun();
    }
}
