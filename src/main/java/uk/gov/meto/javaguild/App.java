package uk.gov.meto.javaguild;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.SwingScheduler;

import java.util.concurrent.TimeUnit;

/**
 *
 */
 public class App {

    static PriceService priceService = new PriceService();
    static PriceDisplay priceDisplay = new PriceDisplay();

    static Subscription priceSubscription;
    static Observable<Integer> priceObservable;
    static Subscription averageSubscription;

    public static void main(String[] args) throws InterruptedException {

        priceObservable = priceService
                        .prices(Observable.interval(500, TimeUnit.MILLISECONDS));

        priceSubscription = priceObservable
                        .observeOn(SwingScheduler.getInstance())
                        .subscribe(priceDisplay::setPrice);

        averageSubscription = priceObservable.buffer(5, 1)
                .map(i -> i.stream().mapToInt(n -> n).average().orElse(0.0))
                .observeOn(SwingScheduler.getInstance())
                .subscribe(priceDisplay::setAverage);

    }

}
