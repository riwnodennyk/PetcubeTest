package kulku.ua.petcubetest.server;

import java.util.List;

import kulku.ua.petcubetest.model.PetPost;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by aindrias on 12.03.2015.
 */
public interface Api {
    @GET("/api/test")
    void listPetPosts(Callback<List<PetPost>> cb);
}