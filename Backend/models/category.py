from pydantic import BaseModel


class Category(BaseModel):
    id: int = None
    name: str
    image: str
