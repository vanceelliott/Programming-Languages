(define (main)
    (println "Author: Vance Elliott vanceelliott@westminster.net")
    )

; Problem 1
(println "the phone number I'm using is 1 (404) 555 2346")

(inspect (/ 550 5 2 55))
(inspect (+ 202 200 2))
(inspect (- 1000 445))
(inspect (* 46 (+ 37 14)))

; Problem 2

;(((2*4)+(3+5)*3)+(((10-7)+6))/2)

(inspect
    (+ 
        (* 
            (+ 
                (* 2 4) 
                (+ 3 5)
            ) 
            3
        ) 
        (/ 
            (+ 
                (- 10 7) 
                6
            )
            2
        )
    )
)

; (3 + (4 * (5 / 7)) - (8*4)/(2+3))

(inspect   
    (+ 3
        (-
            (* 4
                (/ 5 7)
            )
            (/
                (* 8 4)
                (+ 2 3)
            )
        )
    )
)







