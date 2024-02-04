from typing import Optional

from pydantic import BaseModel


class Furniture(BaseModel):
    id: int = None
    name: str
    description: str
    image1: str
    image2: Optional[str] = None
    image3: Optional[str] = None
    model3d: str
    price: float
    type_id: int
