import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableSearchComponent } from './components/table-search/table-search.component';
import { TruncatePipe } from './pipes/truncate.pipe';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    TableSearchComponent,
    TruncatePipe
  ],
  exports: [
    TableSearchComponent,
    TruncatePipe
  ]
})
export class SharedModule { }
