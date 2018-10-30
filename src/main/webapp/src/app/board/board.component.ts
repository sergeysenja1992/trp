import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  items = []

  constructor() {
    for (let i = 0; i < 9999; i++) {
      this.items.push(i);
    }
  }

  ngOnInit() {
  }

}
