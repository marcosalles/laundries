# MODELOS
```
user {
	name					STRING
	email					STRING
	password			STRING
	verified			BOOLEAN
	role					ENUM [CLIENT, ADMIN]
	#credit				QUERY (payment - activation)
}

signup {
	email					STRING
	code					STRING
}

token {
	user_id				REF
	expiry				TIMESTAMP
	code					STRING
}

laundry {
	name					STRING
	address				STRING
}

machine {
	laundry_id		REF
	kind					ENUM [WASH, DRY]
	use_price			DOUBLE
	active_until	TIMESTAMP
}

payment {
	user_id				REF
	date					TIMESTAMP
	value					DOUBLE
}

activation {
	user_id				REF
	machine_id		REF
	date					TIMESTAMP
}

request {
	ip
	token
	params
}
```

# ROTAS
```
/admin
      /user
           *crud
           /renew
      /laundry
              *crud
      /machine
              *crud
/user
		 /signup
		 /dashboard
		 /transactions
/login
/logout
```

# LOGICAS

#### API
```
request.save
if (user & user.active & user.credit > 0) {
	machine.start
	return true
}
return false
```
