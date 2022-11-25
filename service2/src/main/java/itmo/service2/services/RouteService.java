package itmo.service2.services;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import itmo.service2.dto.LocationDto;
import itmo.service2.dto.RouteDto;
import itmo.service2.dto.RoutePostDto;
import itmo.service2.entities.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RouteService {

    public final String BASE_URL = getBaseUrl();

    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<List<Route>> findRouteByIds(Integer idFrom, Integer idTo, String order) {

        String URI = BASE_URL + "/routes?from_id=between:"
                + idFrom + ":" + idFrom + "&to_id=between:" + idTo + ":" + idTo +
                "&sort=" + order;
        List<Route> routes = restTemplate.getForObject(URI, List.class);
        return ResponseEntity.ok(routes);
    }

    public ResponseEntity addRouteBetweenLocations(Integer idFrom, Integer idTo, double distance, RouteDto routeDto) {
        System.out.println(routeDto.toString());
        RoutePostDto routePost = new RoutePostDto();
        routePost.setDistance(distance);
        routePost.setName(routeDto.getName());
        routePost.setCoordinates(routeDto.getCoordinates());
        routePost.setFrom(new LocationDto(idFrom));
        routePost.setTo(new LocationDto(idTo));
        System.out.println(routePost);
        String URI = BASE_URL + "/routes";
        return restTemplate.postForEntity(URI, routePost, ResponseEntity.class);
    }

    private String getBaseUrl(){
        Consul client = Consul.builder().build();
        HealthClient agentClient = client.healthClient();
        com.orbitz.consul.model.health.Service s = agentClient.getHealthyServiceInstances("soa-3-1.0-SNAPSHOT").getResponse().get(0).getService();
        return "http://" + s.getAddress() + ":" + s.getPort() + "/" + s.getId();
    }
}


