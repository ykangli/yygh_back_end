import com.atguigu.yygh.hosp.ServiceHospApplication;
import com.atguigu.yygh.hosp.mapper.HospitalSetMapper;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = ServiceHospApplication.class)
public class ServiceHospApplicationTest {

    @Resource
    private HospitalSetMapper hospitalSetMapper;

    @Resource
    private HospitalSetService hospitalSetService;

    @Test
    /**
     * 测试mybatis 测试mapper
     */
    public void test1() {
        System.out.println(("----- selectAll method test1 ------"));
        List<HospitalSet> list = hospitalSetMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<HospitalSet> list = hospitalSetService.list();
        list.forEach(System.out::println);
    }
}
