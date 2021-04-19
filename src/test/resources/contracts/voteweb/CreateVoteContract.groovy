package contracts.voteweb

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url $(consumer('v1/vote'),
                producer("v1/vote"))
        headers {
            contentType(applicationJson())
        }
        body("""
            {               
                "agendaId": "10",
                "associetedCpf": "78088574080",
                "response": "YES"
            }
        """)
    }
    response {
        status 201
        headers {
            contentType(applicationJson())
        }
        body("""
                {

                    "id": {
                        "associetedCpf": "${value(test(anyNonEmptyString()), stub("78088574080"))}",
                        "agendaId": "${value(test(anyInteger()), stub(4))}"
                    },
                    "date": "${value(test(anyNonEmptyString()), stub("2009-11-09T16:36:49.364+0000"))}",
                    "response": "${value(test(anyNonEmptyString()), stub("NO"))}"
                }                  
        """)
    }
}
