package com.itsoumya.ivion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class SpatialControllerTest {
  @Autowired MockMvc mvc;
  @Test void poisReturnsSorted() throws Exception { mvc.perform(get("/api/pois").param("floor", "1").param("x", "0").param("y", "0").param("radius", "20")).andExpect(status().isOk()).andExpect(jsonPath("$[0].label").value("reception")); }
  @Test void poisFiltersFloor() throws Exception { mvc.perform(get("/api/pois").param("floor", "2").param("x", "0").param("y", "0").param("radius", "20")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2)); }
  @Test void transformIdentity() throws Exception { mvc.perform(post("/api/transform").contentType("application/json").content("{\"originX\":0,\"originY\":0,\"scale\":1,\"rotationDeg\":0,\"x\":3,\"y\":4}")).andExpect(status().isOk()).andExpect(jsonPath("$.x").value(3.0)).andExpect(jsonPath("$.y").value(4.0)); }
}
