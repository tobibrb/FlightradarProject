package rest;

        import org.junit.Test;

        import static junit.framework.Assert.assertFalse;
        import static junit.framework.TestCase.assertTrue;
/**
 * Created by Notebook on 07.12.2015.
 */
public class EmailRestServiceTest {
    @Test
    public void sendEmailTest() {
        assertTrue(EmailRestService.sendEmail("success@simulator.amazonses.com","Test","Dies ist ein Toller Test"));
    }

}
