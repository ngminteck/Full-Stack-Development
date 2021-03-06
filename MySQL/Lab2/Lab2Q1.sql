use hr;

select date_format(hire_date, '%Y') as year,count(date_format(hire_date, '%Y')) as no
from employees
group by year;


select date_format(hire_date, '%M') as month, Count(date_format(hire_date, '%M')) as no
from employees
group by month
order by no desc
limit 1;

select department_id as Dept_No, date_format(hire_date, '%M') as month, Count(date_format(hire_date, '%M')) as no
from employees
where department_id = 60
group by month
order by no desc
limit 1;

-- 4) WAQ to display highest paid employee information in each department.

select department_id, employee_id, first_name, salary
from employees
group by department_id
having max(salary);

-- 5) WAQ to calculate second highest salary department wise.
select department_id, employee_id, first_name, salary
from employees
where salary not in (select max(salary) 
					from employees)
group by department_id;





