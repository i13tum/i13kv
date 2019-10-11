package de.tum.i13;

import de.tum.i13.server.kv.KVStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by chris on 08.01.15.
 */
public class KVStoreTest {
    KVStore store;

    @BeforeEach
    public void setUp() throws Exception {
        store = new KVStore();
    }

    @Test
    public void should_be_ok_to_plain_put_a_value() {
        String putRes = store.process("PUT table key testvale");
        assertThat(putRes, is("OK\r\n"));
    }

    @Test
    public void values_which_we_put_should_be_returned_when_we_get_them_again() {
        store.process("PUT table key testvalue\r\n");
        String getRes = store.process("GET table key\r\n");

        assertThat(getRes, is("testvalue\r\n"));
    }

    @Test
    public void crap_should_not_be_understood(){
        String crap = store.process("CRAP");
        assertThat(crap, containsString("DID_NOT_UNDERSTAND"));
    }

    @Test
    public void half_put_should_result_in_error(){
        String putRes = store.process("PUT table key");
        assertThat(putRes, containsString("ERROR"));
    }

    @Test
    public void sending_only_get_without_key_should_return_an_error(){
        String getRes = store.process("GET");
        assertThat(getRes, containsString("ERROR"));
    }

    @Test
    public void delete_not_existing_key() {
        String getRes = store.process("DELETE table key\r\n");
        assertThat(getRes, containsString("NOT_FOUND"));
    }

    @Test
    public void delete_existing_key() {
        store.process("PUT table key value\r\n");
        String getRes = store.process("DELETE table key\r\n");
        assertThat(getRes, containsString("DELETED"));
    }

    @Test
    public void non_existing_key_should_return_not_found() {
        String res = store.process("EXISTS table key\r\n");
        assertThat(res, containsString("NOT_FOUND"));
    }

    @Test
    public void exist_should_return_exists_on_existing_key() {
        store.process("PUT table key value\r\n");
        String res = store.process("EXISTS table key\r\n");
        assertThat(res, containsString("EXISTS"));
    }
}
