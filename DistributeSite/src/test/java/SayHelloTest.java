import com.apeny.httprpc.JsonResult;
import com.apeny.httprpc.JsonUtil;
import com.apeny.httprpc.domain.Person;
import org.junit.Test;

/**
 * Created by ahu on 2017年09月16日.
 */
public class SayHelloTest {

    @Test
    public void testJson() {
        JsonResult result = new JsonResult();
        result.setCode("2012");
        result.setMessage("ok");
        result.setResult(new Person(1, "new", "new york"));
        String jsonString = JsonUtil.toJson(result);
        System.out.println("toJson...: " + jsonString);
        JsonResult response = JsonUtil.toObject(jsonString, JsonResult.class);
        System.out.println("jsonObject...: " + response);
    }
}
