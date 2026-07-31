package ec.edu.uteq.sgroas.security;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class LoginRateLimiter {

    private final ConcurrentHashMap<String, int[]> intentos = new ConcurrentHashMap<>();
    private static final int MAX_INTENTOS = 6;
    private static final long VENTANA_MS = 60_000;

    public boolean estaBloqueado(String ip) {
        int[] datos = intentos.get(ip);
        if (datos == null) return false;
        long ahora = System.currentTimeMillis();
        if (ahora - datos[1] > VENTANA_MS) {
            intentos.remove(ip);
            return false;
        }
        return datos[0] >= MAX_INTENTOS;
    }

    public void registrarIntentoFallido(String ip) {
        intentos.compute(ip, (k, v) -> {
            if (v == null) return new int[]{1, (int) System.currentTimeMillis()};
            if (System.currentTimeMillis() - v[1] > VENTANA_MS) return new int[]{1, (int) System.currentTimeMillis()};
            v[0]++;
            return v;
        });
    }

    public void resetear(String ip) {
        intentos.remove(ip);
    }
}
