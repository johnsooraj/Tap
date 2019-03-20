import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'app-table-search',
  templateUrl: './table-search.component.html',
  styleUrls: ['./table-search.component.css']
})
export class TableSearchComponent implements OnInit {
  @Input() searchOptions: Array<string>;
  @Output() tableSearched = new EventEmitter<any>();

  searchField: FormControl = new FormControl('');
  searchText: FormControl = new FormControl('');

  constructor() {}

  ngOnInit() {
    this.searchField.setValue(this.searchOptions[0]);
    this.searchField.valueChanges.subscribe(() => {
      if (this.searchText.value && this.searchText.value.trim().length > 0) {
        this.tableSearched.emit({
          isSearch: true,
          searchField: this.searchField.value,
          searchText: this.searchText.value
        });
      }
    });
    this.searchText.valueChanges
      .pipe(
        debounceTime(750),
        distinctUntilChanged()
      )
      .subscribe(() => {
        if (this.searchText.value && this.searchText.value.trim().length > 0) {
          this.tableSearched.emit({
            isSearch: true,
            searchField: this.searchField.value,
            searchText: this.searchText.value
          });
        } else {
          this.tableSearched.emit({
            isSearch: false
          });
        }
      });
  }
}
