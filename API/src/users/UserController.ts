import { NextFunction, Request, Response} from 'express';
import UserSchema from "./UserSchema";
import bcrypt from "bcrypt";

const register = async (req: Request, res: Response, next: NextFunction) => {
  const { name, email, password } = req.body;
  if (!name || !email || !password) {
    return res.status(400).json({error: 'Please enter a valid email'});
  }
  const user = await UserSchema.findOne({email});
  if (user) {
    return res.status(400).json({error: 'User already exists'});
  }
  try {
    const hashedPassword = await bcrypt.hash(password, 10);
    const newUser = await UserSchema.create({
      name,
      email,
      password: hashedPassword,
    });
    return res.status(201).json({
      status: true,
      message: 'User created',
      data: { _id: newUser._id, email: newUser.email},
    });
  } catch (error) {
    return res.status(500).json({error: 'qualcosa non è andato a buon fine :('});
  }
};

const login = async (req: Request, res: Response, next: NextFunction) => {
  res.json({ message: "Hai effettuato il login" });
};

const me = async (req: Request, res: Response, next: NextFunction) => {
  res.json({ message: "memememe" });
}

export { register, login, me };
