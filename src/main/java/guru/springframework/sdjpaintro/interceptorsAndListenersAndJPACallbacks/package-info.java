package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks;

// From all of those 3 techniques, interceptors, listeners and jpa callbacks, jpa callbacks are the best. As in 
// interceptors and listeners when an entity is updated by a listener or interceptor, there is a hibernate query run 
// to execute this update in hibernate session, what is visible in logs when executing the tests.
// When using jpa callbacks, there is no extra hibernate query when callback is executed. Also, jpa callbacks are the 
// easiest to implement.
