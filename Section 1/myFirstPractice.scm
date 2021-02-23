(define (fib x)
    (fibHelper 0 0 x)
)
(define (fibHelper x n c)
    (cond ((< n 1) (fibHelper 0 1 c))
        ((< n (+ c 1)) (fibHelper n (+ x n) c))
        (else n)
    )
)

(inspect (fib 13))






