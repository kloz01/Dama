import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  standalone: true,
    imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required,Validators.email]),
      password: new FormControl('', [Validators.required]),
    })
  }
  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value);
    }
  }

}

