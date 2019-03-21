# Rx Single Error Handling

This shows a repro case for https://github.com/ReactiveX/RxJava/issues/6438

In this case the same exact code will crash the app when using `Observable.fromCallable` but not when using `Single.fromCallable`.

The effect is that fatal exceptions thrown in the on error message like `VirtualMachineError`s and `OutOfMemoryError`s will be silently swallowed.

Workaround is to avoid using `Single.fromCallable` and replace it with `Observable.fromCallable`.
