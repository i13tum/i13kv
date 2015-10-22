package de.tum.i13;

import de.tum.i13.server.kv.KVStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by chris on 08.01.15.
 */
public class KVStoreTest {
    KVStore store;

    @Before
    public void setUp() throws Exception {
        store = new KVStore();
    }

    @Test
    public void should_be_ok_to_plain_put_a_value() {
        String putRes = store.process("PUT table key testvale");
        Assert.assertThat(putRes, is("OK\r\n"));
    }

    @Test
    public void values_which_we_put_should_be_returned_when_we_get_them_again() {
        store.process("PUT table key testvalue\r\n");
        String getRes = store.process("GET table key\r\n");

        Assert.assertThat(getRes, is("testvalue\r\n"));
    }

    @Test
    public void crap_should_not_be_understood(){
        String crap = store.process("CRAP");
        Assert.assertThat(crap, containsString("DID_NOT_UNDERSTAND"));
    }

    @Test
    public void half_put_should_result_in_error(){
        String putRes = store.process("PUT table key");
        Assert.assertThat(putRes, containsString("ERROR"));
    }

    @Test
    public void sending_only_get_without_key_should_return_an_error(){
        String getRes = store.process("GET");
        Assert.assertThat(getRes, containsString("ERROR"));
    }

    @Test
    public void delete_not_existing_key() {
        String getRes = store.process("DELETE table key\r\n");
        Assert.assertThat(getRes, containsString("NOT_FOUND"));
    }

    @Test
    public void delete_existing_key() {
        store.process("PUT table key value\r\n");
        String getRes = store.process("DELETE table key\r\n");
        Assert.assertThat(getRes, containsString("DELETED"));
    }


}
