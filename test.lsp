((lambda ()

	(define 'null (lambda (x) (cond (x nil) (t t))))
	
	(define 'last (lambda (x)
		(cond
			((null (cdr x)) (car x))
			(t (last (cdr x))))))
	
	(cond 
		((last '(1 2 3 nil)) "Test Failed") 
		(t "Test Passed"))

))




