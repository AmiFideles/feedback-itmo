package ru.niu.itmo.feedback.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author amifideles
 */
@Service
public class SeedService {
    private final List<Integer> seeds;

    public SeedService() {
        this.seeds = calculateSeed();
    }

    private List<Integer> calculateSeed() {
        List<Integer> primes = new ArrayList<>();

        for (int i = 3; i <= 50; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }

        return primes;
    }

    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    public int getRandomSeed() {
        int randomIndex = new Random().nextInt(seeds.size());
        return seeds.get(randomIndex);
    }
}
