from pydantic import BaseModel


class User(BaseModel):
    phone_number: str
    name: str
    password: str = None
    address: str = None
