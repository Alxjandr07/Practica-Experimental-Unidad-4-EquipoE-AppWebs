import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-placeholder',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './placeholder.html',
  styleUrl: './placeholder.scss'
})
export class Placeholder {
  private route = inject(ActivatedRoute);
  title$ = this.route.data;
}