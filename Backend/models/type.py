
from pydantic import BaseModel


class Type(BaseModel):
    id: int = None
    name: str
    image: str
    category_id: int
