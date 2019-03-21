import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class Main {
    public static void main(String[] args) {
        // This does not crash the app.
        Single.fromCallable(() -> {
            throw new RuntimeException("foo");
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(
                        object -> {
                        },
                        throwable -> {
                            // This rethrown OutOfMemoryError is silently swallowed.
                            throw new OutOfMemoryError("Thrown from Single and swallowed");
                        });


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // This *does* crash the app.
        Observable.fromCallable(() -> {
            throw new RuntimeException("foo");
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(
                        object -> {
                        },
                        throwable -> {
                            // This rethrown OutOfMemoryError crashes the app.
                            throw new OutOfMemoryError("Thrown from Observable");
                        });


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
